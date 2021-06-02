import {Component, OnDestroy, OnInit} from '@angular/core';
import {TaskService} from '../../../serivce/task.service';
import {RoadMapService} from '../../../serivce/road-map.service';
import {KpiService} from '../../../serivce/kpi.service';
import {SprintService} from '../../../serivce/sprint.service';
import {MemberService} from '../../../serivce/member.service';
import {MemberToDisplay} from '../../../models/member/member-to-display';
import {RoadMapToDisplay} from '../../../models/road-map/road-map-to-display';
import {TaskToDisplay} from '../../../models/task/task-to-display';
import {KpiToDisplay} from '../../../models/kpi/kpi-to-display';
import {SprintToDisplay} from '../../../models/sprint/sprint-to-display';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit, OnDestroy {

  sprints: SprintToDisplay[];
  kpis: KpiToDisplay[];
  tasks: TaskToDisplay[];
  members: MemberToDisplay[];
  roadMaps: RoadMapToDisplay[];

  private subscriptions: Subscription[] = [];

  height: '60px';

  constructor(private taskService: TaskService,
              private roadMapService: RoadMapService,
              private kpiService: KpiService,
              private sprintService: SprintService,
              private memberService: MemberService) {
  }

  ngOnInit(): void {
    const getAllRoadMapsSubscription = this.roadMapService.getAll().subscribe(value => this.roadMaps = value);
    const getAllKpisSubscription = this.kpiService.getAll().subscribe(value => this.kpis = value);
    const getAllTasksSubscription = this.taskService.getAll().subscribe(value => this.tasks = value);

    const getAllActiveSprintsSubscription = this.sprintService.getAllByParams('true', 'active').subscribe(value => this.sprints = value);
    const getAllMembersSubscription = this.memberService.getAll().subscribe(value => this.members = value);

    this.subscriptions.push(getAllKpisSubscription,
      getAllRoadMapsSubscription,
      getAllTasksSubscription,
      getAllActiveSprintsSubscription,
      getAllMembersSubscription);
  }

  numberToString(id: number): string {
    return String(id);
  }

  ngOnDestroy(): void {
    for (const subscription of this.subscriptions) {
      if (subscription) {
        subscription.unsubscribe();
      }
    }
  }
}
