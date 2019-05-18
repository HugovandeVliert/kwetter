import { Component, Input, OnInit } from '@angular/core';

import { Kweet } from "../../_models/kweet";

@Component({
  selector: 'app-kweet',
  templateUrl: './kweet.component.html',
  styleUrls: ['./kweet.component.css']
})
export class KweetComponent implements OnInit {
  @Input() kweet: Kweet;

  constructor() {
  }

  ngOnInit() {
  }
}
