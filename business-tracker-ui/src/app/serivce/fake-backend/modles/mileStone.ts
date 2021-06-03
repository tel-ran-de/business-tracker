import {Task} from './task';
import {Kpi} from './kpi';

export class MileStone {
  id: number;
  roadMapId: number;

  name: string;
  startDate: Date;
  finishDate: Date;

  sprints: Task[];
  kpis: Kpi[];
}
