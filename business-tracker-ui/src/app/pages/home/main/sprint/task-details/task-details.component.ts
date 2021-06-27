import {Component, OnDestroy, OnInit} from '@angular/core';
import {ResourceService} from '../../../../../serivce/resource.service';
import {MemberService} from '../../../../../serivce/member.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {MileStoneToDisplay} from '../../../../../models/mile-stone/mile-stone-to-display';
import {MileStoneService} from '../../../../../serivce/mile-stone.service';

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
export class TaskDetailsComponent implements OnInit, OnDestroy {

  mileStoneToDisplay: MileStoneToDisplay;
  subscriptions: Subscription[] = [];
  taskFound = true;

  constructor(private resourceService: ResourceService,
              private memberService: MemberService,
              private mileStoneService: MileStoneService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const mileStoneId = +this.route.snapshot.paramMap.get('mileStoneId');
    const getMileStonekSub = this.mileStoneService.getMileStoneById(mileStoneId)
      .subscribe(value => this.mileStoneToDisplay = value);
    this.subscriptions.push(getMileStonekSub);
  }

  getStatus(ms: MileStoneToDisplay): number {
    const startDate = new Date(ms.startDate).getTime();
    const endDate = new Date(ms.finishDate).getTime();
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
