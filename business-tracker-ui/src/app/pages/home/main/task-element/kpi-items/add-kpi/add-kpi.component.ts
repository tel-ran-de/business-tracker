import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {KpiService} from '../../../../../../serivce/kpi.service';
import {Subscription} from 'rxjs';
import {KpiToAdd} from '../../../../../../models/kpi/kpi-to-add';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {KpiToDisplay} from '../../../../../../models/kpi/kpi-to-display';

@Component({
  selector: 'app-add-kpi',
  templateUrl: './add-kpi.component.html',
  styleUrls: ['./add-kpi.component.css']
})
export class AddKpiComponent implements OnInit, OnDestroy {

  @Input()
  taskId: number;
  @Output()
  saved: EventEmitter<KpiToDisplay> = new EventEmitter<KpiToDisplay>();

  private subscriptions: Subscription[] = [];

  form: FormGroup;

  constructor(private kpiService: KpiService) {
  }

  ngOnInit(): void {
    this.form = new FormGroup({
      name: new FormControl('', Validators.required)
    });
  }

  onClickSave(): void {
    const kpiToAdd: KpiToAdd = new KpiToAdd();
    kpiToAdd.name = this.form.controls.name.value;
    kpiToAdd.taskId = this.taskId;

    const addKpiSub = this.kpiService.add(kpiToAdd)
      .subscribe(value => {
        // тригирю о добовлении кпи
        this.kpiService.kpiAdded$.next(value);
        this.saved.emit(value);
      });

    this.subscriptions.push(addKpiSub);
  }

  ngOnDestroy(): void {
    for (const subscription of this.subscriptions) {
      if (subscription) {
        subscription.unsubscribe();
      }
    }
  }
}
