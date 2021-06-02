import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TaskService} from '../../../../serivce/task.service';
import {RoadMapService} from '../../../../serivce/road-map.service';
import {ResourceService} from '../../../../serivce/resource.service';
import {KpiService} from '../../../../serivce/kpi.service';
import {ResourceToDisplay} from '../../../../models/resource/resource-to-display';
import {KpiToDisplay} from '../../../../models/kpi/kpi-to-display';
import {TaskToDisplay} from '../../../../models/task/task-to-display';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit, OnDestroy {

  roadMapId: string;
  taskId: string;

  resources: ResourceToDisplay[];
  kpis: KpiToDisplay[];
  tasks: TaskToDisplay[];

  private subscriptions: Subscription[] = [];

  constructor(private router: Router,
              private route: ActivatedRoute,
              public taskService: TaskService,
              private roadMapService: RoadMapService,
              private resourceService: ResourceService,
              public kpiService: KpiService) {
  }

  ngOnInit(): void {
    this.taskId = this.route.snapshot.paramMap.get('taskId');
    this.roadMapId = this.route.snapshot.paramMap.get('roadMapId');

    const getAllResourcesSubscription = this.resourceService.getAll()
      .subscribe(resourceArr => this.resources = resourceArr);

    this.taskService.getAll()
      .subscribe(value => this.tasks = value);

    const getAllKpisSubscription = this.kpiService.getAll()
      .subscribe(value => this.kpis = value);


    const kpiAddSubscription = this.kpiService.kpiAdded$
      .subscribe(value => this.kpis.push(value));

    const kpiDeleteSubscription = this.kpiService.kpiDeleted$
      .subscribe(value => {
        const removeKpiItem = this.kpis.indexOf(value);
        this.kpis.splice(removeKpiItem, 1);
      });

    const reloadResourceListOnRemoveSubscription = this.resourceService.reloadResourceListRemoved$
      .subscribe(value => {
        const indexToRemove = this.resources.indexOf(value);
        this.resources.splice(indexToRemove, 1);
      });

    const reloadResourceListSubscription = this.resourceService.reloadAddedResourceList$
      .subscribe(value => {
        this.resources = value;
      });

    this.subscriptions.push(
      getAllKpisSubscription,
      kpiAddSubscription,
      kpiDeleteSubscription,
      getAllResourcesSubscription,
      reloadResourceListSubscription,
      reloadResourceListOnRemoveSubscription
    );
  }

  ngOnDestroy(): void {
    for (const subscription of this.subscriptions) {
      if (subscription) {
        subscription.unsubscribe();
      }
    }
  }

  kpisByTaskId(taskId: number): KpiToDisplay[] {
    return this.kpis.filter(value => value.taskId === taskId);
  }
}
