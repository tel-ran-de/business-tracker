import {Component, OnDestroy, OnInit} from '@angular/core';
import {ResourceService} from '../../../../../serivce/resource.service';
import {MemberService} from '../../../../../serivce/member.service';
import {DeliveryService} from '../../../../../serivce/delivery.service';
import {ResponsibleMembersService} from '../../../../../serivce/responsible-members.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {SprintService} from '../../../../../serivce/sprint.service';
import {TaskToDisplay} from '../../../../../models/task/task-to-display';
import {TaskService} from '../../../../../serivce/task.service';

@Component({
  selector: 'app-sprint-details',
  templateUrl: './sprint-details.component.html',
  styleUrls: ['./sprint-details.component.css']
})
export class SprintDetailsComponent implements OnInit, OnDestroy {

  task: TaskToDisplay;
  subscriptions: Subscription[] = [];
  sprintFound = true;

  constructor(private resourceService: ResourceService,
              private sprintService: SprintService,
              private memberService: MemberService,
              private taskService: TaskService,
              private responsibleMembersService: ResponsibleMembersService,
              private deliveryService: DeliveryService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    // const roadMapId = this.route.snapshot.paramMap.get('roadMapId');
    // const sprintId = this.route.snapshot.paramMap.get('sprintId');
    const taskId = this.route.snapshot.paramMap.get('taskId');
    const getTaskSub = this.taskService.getById(taskId)
      .subscribe(value => this.task = value);
    this.subscriptions.push(getTaskSub);

  }

  getStatus(task: TaskToDisplay): number {
    const startDate = new Date(task.startDate).getTime();
    const endDate = new Date(task.finishDate).getTime();
    const now = new Date().getTime();

    const q = now - startDate;
    const d = endDate - startDate;

    return Math.round((q / d) * 100);
  }

  ngOnDestroy(): void {
    for (const subscription of this.subscriptions) {
      if (subscription) {
        subscription.unsubscribe();
      }
    }
  }
}
