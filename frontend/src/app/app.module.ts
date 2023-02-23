import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { HomeComponent } from './components/home.component';
import { PostingComponent } from './components/posting.component';
import { ConfirmComponent } from './components/confirm.component';

const appRoutes: Routes =[
  {path: "", component: HomeComponent},
  {path: "posting/:postingId", component: PostingComponent},
 
  {path: "confirmPost/:postingId", component: ConfirmComponent},
  {path: '**', redirectTo: '', pathMatch: 'full'}
]

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PostingComponent,
    ConfirmComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
