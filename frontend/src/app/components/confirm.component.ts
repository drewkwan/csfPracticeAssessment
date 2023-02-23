import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.css']
})
export class ConfirmComponent implements OnInit {

  routeSub$!: Subscription;
  postingId!: string;
  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
      this.routeSub$= this.activatedRoute.params.subscribe(params => {
        this.postingId =params['postingId'];
      })

  }
}
