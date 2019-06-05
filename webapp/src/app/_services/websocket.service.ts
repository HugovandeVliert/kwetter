import { Injectable } from '@angular/core';
import { StompService } from '@stomp/ng2-stompjs';
import { IMessage } from '@stomp/stompjs';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';

@Injectable({providedIn: 'root'})
export class WebsocketService {
  private stompService: StompService;

  constructor() {
    this.initializeWebSocketConnection();
  }

  getTimelineUpdateNotifications(userId: number): Observable<IMessage> {
    return this.stompService.subscribe('/timeline/' + userId);
  }

  getNewFollowerNotifications(userId: number): Observable<IMessage> {
    return this.stompService.subscribe('/new-follower/' + userId);
  }

  private initializeWebSocketConnection(): void {
    this.stompService = new StompService({
      url: `${environment.websocketEndpoint}`,
      headers: {
        login: '',
        passcode: ''
      },
      heartbeat_in: 0,
      heartbeat_out: 20000,
      reconnect_delay: 5000,
      debug: false,
    });
  }
}
