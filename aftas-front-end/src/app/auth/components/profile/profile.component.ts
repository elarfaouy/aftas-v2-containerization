import {Component, OnInit} from '@angular/core';
import {User} from "../../../models/user";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  user!: User;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.data.subscribe(
      (value) => {
        this.user = value["userData"];
      }
    );
  }
}
