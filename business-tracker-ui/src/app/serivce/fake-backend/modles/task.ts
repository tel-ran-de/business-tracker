import {Sprint} from './sprint';
import {Kpi} from './kpi';

export class Task {
  id: number;
  roadMapId: number;

  statusBarValue: number;

  name: string;
  startDate: Date;
  finishDate: Date;

  sprints: Sprint[];
  kpis: Kpi[];
}
