import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Posting } from '../models';
import { PostingService } from '../services/posting.service';
import { HomeComponent } from './home.component';

@Component({
  selector: 'app-posting',
  templateUrl: './posting.component.html',
  styleUrls: ['./posting.component.css']
})
export class PostingComponent implements OnInit, OnDestroy {

  postingId!: string;
  routeSub$!: Subscription;
  posting!: Posting;
  form!: FormGroup;


  constructor(private activatedRoute: ActivatedRoute, private postingSvc: PostingService, private fb: FormBuilder, private router: Router) { }

  ngOnInit(): void {
    console.log(this.activatedRoute);
    this.routeSub$= this.activatedRoute.params.subscribe(params=> {
      this.postingId=params['postingId'];
      console.log(this.postingId);
    })
    this.getPosting();
  }

  getPosting() {
    this.postingSvc.getPosting(this.postingId).then((response)=> {
      this.posting=response;
   }).catch(error=> {
    console.error(error);
   })
  }

  confirmPosting() {
    console.log(this.posting);
    this.postingId= this.posting.postingId;
    this.postingSvc.putPosting(this.postingId).then((response)=> {
      console.log(response);
      this.router.navigate([`confirmPost/${this.postingId}`]);
    }).catch(error=> {
      console.error(error)
      alert()
    })

  }

  ngOnDestroy() {
    this.routeSub$.unsubscribe();
  }



}
