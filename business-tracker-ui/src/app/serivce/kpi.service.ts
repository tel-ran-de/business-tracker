import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HttpOperation} from './http-operation';
import {KpiToDisplay} from '../models/kpi/kpi-to-display';
import {KpiToAdd} from '../models/kpi/kpi-to-add';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class KpiService extends HttpOperation<KpiToDisplay, KpiToAdd> {

  kpiAdded$: Subject<KpiToDisplay> = new Subject<KpiToDisplay>();
  kpiDeleted$: Subject<KpiToDisplay> = new Subject<KpiToDisplay>();

  constructor(private httpClient: HttpClient) {
    super('api/kpi', 'api/kpi', 'api/kpi', 'api/kpi', httpClient);
  }
}
