import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HttpOperation} from './http-operation';
import {KpiToDisplay} from '../models/kpi/kpi-to-display';
import {KpiToAdd} from '../models/kpi/kpi-to-add';
import {Observable, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class KpiService extends HttpOperation<KpiToDisplay, KpiToAdd> {

  kpiAdded$: Subject<KpiToDisplay> = new Subject<KpiToDisplay>();
  kpiDeleted$: Subject<KpiToDisplay> = new Subject<KpiToDisplay>();

  constructor(private httpClient: HttpClient) {
    super('api/kpi', 'api/kpi', 'api/kpi', 'api/kpi', httpClient);
  }

  addKpi(msId: number, kpi: KpiToAdd): Observable<void> {
    const url = `api/kpis/${msId}`
    return this.httpClient.post<void>(url, kpi);
  }

  getKpisByMileStoneId(msId: number): Observable<KpiToDisplay[]> {
    const url = `api/milestones/${msId}/kpis`
    return this.httpClient.get<KpiToDisplay[]>(url);
  }

  getKpisByRoadMapId(roadMapId: number): Observable<KpiToDisplay[]> {
    const url = `api/projects/${roadMapId}/kpis`
    return this.httpClient.get<KpiToDisplay[]>(url);
  }

  getKpisByProjectId(projectId: number): Observable<KpiToDisplay[]> {
    const url = `api/projects/${projectId}/kpis`
    return this.httpClient.get<KpiToDisplay[]>(url);
  }
}
