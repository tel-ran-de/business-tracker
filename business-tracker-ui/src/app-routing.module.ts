import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './app/pages/home/home.component';
import {AppComponent} from './app/app.component';
import {MainComponent} from './app/pages/home/main/main.component';
import {MileStoneComponent} from './app/pages/home/main/mile-stone/mile-stone.component';
import {TaskComponent} from './app/pages/home/main/sprint/task.component';
import {TaskDetailsComponent} from './app/pages/home/main/sprint/task-details/task-details.component';
import {PageNotFoundComponent} from "./app/pages/page-not-found/page-not-found.component";

const routes: Routes = [
  {
    path: '', component: AppComponent, children: [
      {path: '', component: HomeComponent},
      {path: 'products/:projectId', component: MainComponent},
      {path: 'products/:projectId/road-maps/:roadMapId', component: MileStoneComponent},
      {path: 'products/:projectId/road-maps/:roadMapId/mile-stones/:mileStoneId/task', component: TaskComponent},
      {
        path: 'products/:projectId/road-maps/:roadMapId/mile-stones/:mileStoneId/task/:taskId',
        component: TaskDetailsComponent
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
