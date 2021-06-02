import {Component, Input} from '@angular/core';
import {TaskToDisplay} from '../../../../models/task/task-to-display';
import {KpiToDisplay} from '../../../../models/kpi/kpi-to-display';

@Component({
  selector: 'app-task-element',
  templateUrl: './task-element.component.html',
  styleUrls: ['./task-element.component.css']
})
export class TaskElementComponent {

  @Input()
  task: TaskToDisplay;
  @Input()
  kpis: KpiToDisplay[];

  getStatus(task: TaskToDisplay): number {
    const startDate = new Date(task.startDate).getTime();
    const endDate = new Date(task.finishDate).getTime();
    const now = new Date().getTime();

    const q = now - startDate;
    const d = endDate - startDate;

    return Math.round((q / d) * 100);
  }
}

