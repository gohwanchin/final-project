import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { SearchComponent } from './components/search/search.component';
import { SearchService } from './services/search.service';
import { ResultsComponent } from './components/results/results.component';
import { TvDetailsComponent } from './components/tv-details/tv-details.component';
import { ErrorComponent } from './components/error/error.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { UserService } from './services/user.service';
import { AuthService } from './services/auth.service';
import { AuthInterceptor } from './auth/auth.interceptor';
import { AuthGuard } from './auth/auth.guard';
import { LoginGuard } from './auth/login.guard';
import { WatchlistComponent } from './components/watchlist/watchlist.component';
import { ProfileComponent } from './components/profile/profile.component';

const appRoutes: Routes = [
  { path: '', component: LoginComponent, canActivate: [LoginGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [LoginGuard] },
  { path: 'search', component: SearchComponent, canActivate: [AuthGuard] },
  { path: 'results', component: ResultsComponent, canActivate: [AuthGuard] },
  { path: 'watchlist', component: WatchlistComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'tv/:id', component: TvDetailsComponent, canActivate: [AuthGuard] },
  { path: 'error/:code', component: ErrorComponent },
  { path: '**', redirectTo: '/error/404', pathMatch: 'full' }
]

@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    ResultsComponent,
    TvDetailsComponent,
    ErrorComponent,
    LoginComponent,
    RegisterComponent,
    WatchlistComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    FormsModule, ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, { useHash: true })
  ],
  providers: [SearchService, UserService,
    AuthService, AuthGuard, LoginGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
