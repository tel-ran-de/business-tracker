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
      {path: 'products/:productId', component: MainComponent},
      {path: 'products/:productId/road-maps/:roadMapId', component: MileStoneComponent},
      {path: 'products/:productId/road-maps/:roadMapId/mile-stones/:taskId/sprint', component: TaskComponent},
      {
        path: 'products/:productId/road-maps/:roadMapId/mile-stones/:taskId/sprint/:sprintId',
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
