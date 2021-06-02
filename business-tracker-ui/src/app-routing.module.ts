import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './app/pages/home/home.component';
import {AppComponent} from './app/app.component';
import {MainComponent} from './app/pages/home/main/main.component';
import {TaskComponent} from './app/pages/home/main/task/task.component';
import {SprintComponent} from './app/pages/home/main/sprint/sprint.component';
import {SprintDetailsComponent} from './app/pages/home/main/sprint/sprint-details/sprint-details.component';
import {PageNotFoundComponent} from "./app/pages/page-not-found/page-not-found.component";


const routes: Routes = [
  {
    path: '', component: AppComponent, children: [
      {path: '', component: HomeComponent},
      {path: 'products/:productId', component: MainComponent},
      {path: 'products/:productId/road-maps/:roadMapId', component: TaskComponent},
      {path: 'products/:productId/road-maps/:roadMapId/mile-stones/:taskId/sprint', component: SprintComponent},
      {
        path: 'products/:productId/road-maps/:roadMapId/mile-stones/:taskId/sprint/:sprintId',
        component: SprintDetailsComponent
      },
    ]
  },

  {path: '404', component: PageNotFoundComponent},
  {path: '**', redirectTo: '/404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
