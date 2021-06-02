import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app/app.component';
import {HomeComponent} from './app/pages/home/home.component';
import {AppRoutingModule} from './app-routing.module';
import {MainComponent} from './app/pages/home/main/main.component';
import {RouterModule} from '@angular/router';
import {NgbAlertModule, NgbModule, NgbPaginationModule} from '@ng-bootstrap/ng-bootstrap';
import {TaskComponent} from './app/pages/home/main/task/task.component';
import {TimeLineComponent} from './app/pages/home/main/time-line/time-line.component';
import {SprintComponent} from './app/pages/home/main/sprint/sprint.component';
import {TaskElementComponent} from './app/pages/home/main/task-element/task-element.component';
import {AddSprintComponent} from './app/pages/home/main/sprint/add-sprint/add-sprint.component';
import {AddResourceComponent} from './app/pages/home/main/sprint/add-resource/add-resource.component';
import {SprintDetailsComponent} from './app/pages/home/main/sprint/sprint-details/sprint-details.component';
import {FilterActiveSprintsPipe} from './app/serivce/pipes/filtern-active-sprints.pipe';
import {HttpClientModule} from '@angular/common/http';
import {PreLoaderComponent} from './app/pages/pre-loader/pre-loader.component';
import {PreLoaderSmallComponent} from './app/pages/pre-loader-small/pre-loader-small.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ResourceListComponent} from './app/pages/home/main/sprint/resource-list/resource-list.component';
import {KpiItemsComponent} from './app/pages/home/main/task-element/kpi-items/kpi-items.component';
import {AddKpiComponent} from './app/pages/home/main/task-element/kpi-items/add-kpi/add-kpi.component';
import {SprintElementComponent} from './app/pages/home/main/sprint/sprint-element/sprint-element.component';
import {DeleteConfirmationModalComponent} from './app/pages/home/main/delete-confirmation-modal/delete-confirmation-modal.component';
import {InMemoryDataService} from './app/serivce/fake-backend/in-memory-data.service';
import {HttpClientInMemoryWebApiModule} from "angular-in-memory-web-api";
import {EditSprintComponent} from './app/pages/home/main/sprint/edit-sprint/edit-sprint.component';
import { PageNotFoundComponent } from './app/pages/page-not-found/page-not-found.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MainComponent,
    TaskComponent,
    TimeLineComponent,
    SprintComponent,
    TaskElementComponent,
    AddSprintComponent,
    AddResourceComponent,
    SprintDetailsComponent,
    FilterActiveSprintsPipe,
    PreLoaderComponent,
    PreLoaderSmallComponent,
    ResourceListComponent,
    KpiItemsComponent,
    AddKpiComponent,
    SprintElementComponent,
    DeleteConfirmationModalComponent,
    EditSprintComponent,
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
    ReactiveFormsModule,
    HttpClientInMemoryWebApiModule.forRoot(
      InMemoryDataService, {dataEncapsulation: false}
    )
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
