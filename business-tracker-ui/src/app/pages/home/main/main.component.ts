import {Component, OnDestroy, OnInit} from '@angular/core';
import {MileStoneService} from '../../../serivce/mile-stone.service';
import {RoadMapService} from '../../../serivce/road-map.service';
import {KpiService} from '../../../serivce/kpi.service';
import {TaskService} from '../../../serivce/task.service';
import {MemberService} from '../../../serivce/member.service';
import {MemberToDisplay} from '../../../models/member/member-to-display';
import {RoadMapToDisplay} from '../../../models/road-map/road-map-to-display';
import {KpiToDisplay} from '../../../models/kpi/kpi-to-display';
import {TaskToDisplay} from '../../../models/task/task-to-display';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit, OnDestroy {

  activeTasks: TaskToDisplay[];
  kpis: KpiToDisplay[];
  members: MemberToDisplay[];
  roadMaps: RoadMapToDisplay[];

  private subscriptions: Subscription[] = [];

  height: '60px';

  constructor(private taskService: TaskService,
              private roadMapService: RoadMapService,
              private kpiService: KpiService,
              private mileStoneService: MileStoneService,
              private memberService: MemberService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const projectId = +this.route.snapshot.paramMap.get("projectId");

    const getAllRoadMapsSubscription = this.roadMapService.getAllRoadMapsByProjectId(projectId).subscribe(value => this.roadMaps = value);
    const getAllKpisByProjectSubscription = this.kpiService.getKpisByProjectId(projectId).subscribe(value => this.kpis = value);

    const getAllActiveTasksSubscription = this.taskService.getAllTasksByProjectAndActive(projectId).subscribe(value => this.activeTasks = value);
    const getAllMembersSubscription = this.memberService.getAllMembersByProjectId(projectId).subscribe(value => this.members = value);

    this.subscriptions.push(getAllKpisByProjectSubscription,
      getAllRoadMapsSubscription,
      getAllActiveTasksSubscription,
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
