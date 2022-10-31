import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { SearchComponent } from './components/search/search.component';
import { SearchService } from './services/search.service';
import { ResultsComponent } from './components/results/results.component';
import { TvDetailsComponent } from './components/tv-details/tv-details.component';
import { ErrorComponent } from './components/error/error.component';

const appRoutes: Routes = [
  {path: '', component: SearchComponent},
  {path: 'results', component: ResultsComponent},
  {path: 'tv/:id', component: TvDetailsComponent},
  {path: 'error/:code', component: ErrorComponent},
  {path: '**', redirectTo: '/', pathMatch: 'full'}
]

@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    ResultsComponent,
    TvDetailsComponent,
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    FormsModule, ReactiveFormsModule,
    HttpClientModule,
		RouterModule.forRoot(appRoutes, { useHash: true })
  ],
  providers: [ SearchService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
