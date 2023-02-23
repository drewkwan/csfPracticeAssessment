import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom} from 'rxjs';
import { Posting } from '../models';

@Injectable({
  providedIn: 'root'
})

export class PostingService {

  posting!: Posting;

  constructor(private http: HttpClient) { }

  createPosting(name:string, email:string, phone: string, title:string, description:string, image: File): Promise<any> {
    const formData = new FormData();
    formData.set("image", image);
    formData.set("name", name);
    formData.set("email", email);
    formData.set("phone", phone);
    formData.set("title", title);
    formData.set("description", description);

    return lastValueFrom(this.http.post("/api/posting", formData));

  }

  getPosting(postId:string): Promise<any> {
    return lastValueFrom(this.http.get(`/api/posting/${postId}`));
  }

  putPosting(postId:string):Promise<any> {
    return lastValueFrom(this.http.put(`api/posting/${postId}`, postId));
  }

}
