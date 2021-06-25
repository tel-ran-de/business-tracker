import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app/app.component';
import {HomeComponent} from './app/pages/home/home.component';
import {AppRoutingModule} from './app-routing.module';
import {MainComponent} from './app/pages/home/main/main.component';
import {RouterModule} from '@angular/router';
import {NgbAlertModule, NgbModule, NgbPaginationModule} from '@ng-bootstrap/ng-bootstrap';
import {MileStoneComponent} from './app/pages/home/main/mile-stone/mile-stone.component';
import {TimeLineComponent} from './app/pages/home/main/time-line/time-line.component';
import {TaskComponent} from './app/pages/home/main/sprint/task.component';
import {AddTaskComponent} from './app/pages/home/main/sprint/add-task/add-task.component';
import {AddResourceComponent} from './app/pages/home/main/sprint/add-resource/add-resource.component';
import {TaskDetailsComponent} from './app/pages/home/main/sprint/task-details/task-details.component';
import {HttpClientModule} from '@angular/common/http';
import {PreLoaderComponent} from './app/pages/pre-loader/pre-loader.component';
import {PreLoaderSmallComponent} from './app/pages/pre-loader-small/pre-loader-small.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ResourceListComponent} from './app/pages/home/main/sprint/resource-list/resource-list.component';
import {TaskElementComponent} from './app/pages/home/main/sprint/task-element/task-element.component';
import {DeleteConfirmationModalComponent} from './app/pages/home/main/delete-confirmation-modal/delete-confirmation-modal.component';
import {EditTaskComponent} from './app/pages/home/main/sprint/edit-task/edit-task.component';
import {PageNotFoundComponent} from './app/pages/page-not-found/page-not-found.component';
import {MileStoneElementComponent} from "./app/pages/home/main/mile-stone/ms-element/mile-stone-element.component";
import {KpiItemsComponent} from "./app/pages/home/main/mile-stone/kpi-items/kpi-items.component";
import {AddKpiComponent} from "./app/pages/home/main/mile-stone/kpi-items/add-kpi/add-kpi.component";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MainComponent,
    MileStoneComponent,
    TimeLineComponent,
    TaskComponent,
    MileStoneElementComponent,
    AddTaskComponent,
    AddResourceComponent,
    TaskDetailsComponent,
    PreLoaderComponent,
    PreLoaderSmallComponent,
    ResourceListComponent,
    KpiItemsComponent,
    AddKpiComponent,
    TaskElementComponent,
    DeleteConfirmationModalComponent,
    EditTaskComponent,
    PageNotFoundComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule,
    NgbModule,
    NgbPaginationModule,
    NgbAlertModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
