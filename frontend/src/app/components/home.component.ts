import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { Posting } from '../models';
import { PostingService } from '../services/posting.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  @ViewChild('file') imageFile!: ElementRef;

  post!: Posting;
  postingId!: string;
  form!: FormGroup;




  constructor(private fb: FormBuilder, private router: Router, private postingSvc: PostingService) {
    
  }

  ngOnInit(): void {
    this.form = this.createForm();
  }

  createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control('', [Validators.required, Validators.minLength(3)]),
      email: this.fb.control('', [Validators.required, Validators.maxLength(128)]),
      phone: this.fb.control(' '),
      title: this.fb.control('', [Validators.required, Validators.minLength(5)]),
      description: this.fb.control('', [Validators.required]),
      image: this.fb.control('', [Validators.required])

    })
  }
  
  submitForm(): void {
    console.log(this.form.value);
    const image: File = this.imageFile.nativeElement.files[0];
    const name = this.form.get('name')?.value
    const email =this.form.get('email')?.value;
    const phone= this.form.get('phone')?.value;
    const title = this.form.get('title')?.value;
    const description=this.form.get('description')?.value;
    this.postingSvc.createPosting(name, email, phone, title, description, image).then((response)=> {
      this.post=response;
      this.postingId=this.post.postingId
      console.log(this.postingId)
      console.log(this.post);
      this.router.navigate([`posting/${this.postingId}`]);
    }).catch((err)=> {
      console.error(err);
      alert(`posting id ${this.postingId} not found!`);
    })

    // this.postingId = this.post.postingId
    
  }

  clearForm() {
    this.form = this.createForm();
    //create the post request, then navigate away
    
    
  }

  isFormValid(): boolean {
    
    return this.form.invalid;
  }

}
