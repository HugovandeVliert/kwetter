import { Component, OnDestroy, OnInit } from '@angular/core';
import { IMessage } from '@stomp/stompjs';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';

import { Kweet } from '../_models/kweet';
import { User } from '../_models/user';
import { AuthenticationService } from '../_services/authentication.service';
import { KweetService } from '../_services/kweet.service';
import { WebsocketService } from '../_services/websocket.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, OnDestroy {
  private subscriptions: Array<Subscription>;
  user: User;
  kweets: Kweet[];

  constructor(
    private authenticationService: AuthenticationService,
    private kweetService: KweetService,
    private websocketService: WebsocketService,
    private toastrService: ToastrService
  ) {
    this.subscriptions = [];
    this.kweets = [];
  }

  ngOnInit(): void {
    this.subscriptions.push(this.authenticationService.currentUser.subscribe(user => {
      this.user = user;
    }));
    this.subscriptions.push(this.websocketService.getNewFollowerNotifications(this.user.id).subscribe((message: IMessage) => {
      this.toastrService.info(message.body);
    }));

    this.kweetService.getAllFromUser(this.user.id).subscribe((kweets: Kweet[]) => {
      this.kweets = kweets;
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription: Subscription) => {
      subscription.unsubscribe();
    });
  }
}
