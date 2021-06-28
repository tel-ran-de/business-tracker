import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MileStoneService} from '../../../../serivce/mile-stone.service';
import {ResourceService} from '../../../../serivce/resource.service';
import {KpiService} from '../../../../serivce/kpi.service';
import {ResourceToDisplay} from '../../../../models/resource/resource-to-display';
import {KpiToDisplay} from '../../../../models/kpi/kpi-to-display';
import {MileStoneToDisplay} from '../../../../models/mile-stone/mile-stone-to-display';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-mile-stone',
  templateUrl: './mile-stone.component.html',
  styleUrls: ['./mile-stone.component.css']
})
export class MileStoneComponent implements OnInit, OnDestroy {

  roadMapId: string;

  resources: ResourceToDisplay[];
  kpis: KpiToDisplay[];
  mileStones: MileStoneToDisplay[];

  private subscriptions: Subscription[] = [];

  constructor(private router: Router,
              private route: ActivatedRoute,
              public mileStoneService: MileStoneService,
              private resourceService: ResourceService,
              public kpiService: KpiService) {
  }

  ngOnInit(): void {
    this.roadMapId = this.route.snapshot.paramMap.get('roadMapId');

    const getAllResourcesSubscription = this.resourceService.getAllResourcesByRoadMap(+this.roadMapId)
      .subscribe(resourceArr => this.resources = resourceArr);
    this.mileStoneService.getAllMileStonesByRoadMapId(+this.roadMapId)
      .subscribe(value => {
        this.mileStones = value;
        this.mileStones.sort((a, b) => a.id - b.id);
      });
    const getAllKpisByMileStoneIdSubscription = this.kpiService.getKpisByRoadMapId(+this.roadMapId)
      .subscribe(value => this.kpis = value);

    const kpiAddSubscription = this.kpiService.kpiAdded$
      .subscribe(kpi => this.kpis.push(kpi));
    const kpiDeleteSubscription = this.kpiService.kpiDeleted$
      .subscribe(kpi => {
        const removeKpiItem = this.kpis.findIndex(value => value.kpi === kpi.kpi);
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
      getAllKpisByMileStoneIdSubscription,
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
}
