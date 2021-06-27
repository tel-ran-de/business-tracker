import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {KpiToDisplay} from '../models/kpi/kpi-to-display';
import {KpiToAdd} from '../models/kpi/kpi-to-add';
import {Observable, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class KpiService {

  options = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  kpiAdded$: Subject<KpiToDisplay> = new Subject<KpiToDisplay>();
  kpiDeleted$: Subject<KpiToDisplay> = new Subject<KpiToDisplay>();

  constructor(private httpClient: HttpClient) {
  }

  addKpi(msId: number, kpi: KpiToAdd): Observable<void> {
    const url = `api/kpis/${msId}`
    return this.httpClient.post<void>(url, kpi, this.options);
  }

  getKpisByMileStoneId(msId: number): Observable<KpiToDisplay[]> {
    const url = `api/milestone/${msId}/kpis`
    return this.httpClient.get<KpiToDisplay[]>(url, this.options);
  }

  getKpisByRoadMapId(roadMapId: number): Observable<KpiToDisplay[]> {
    const url = `api/roadmap/${roadMapId}/kpis`
    return this.httpClient.get<KpiToDisplay[]>(url, this.options);
  }

  getKpisByProjectId(projectId: number): Observable<KpiToDisplay[]> {
    const url = `api/project/${projectId}/kpis`
    return this.httpClient.get<KpiToDisplay[]>(url, this.options);
  }

  removeKpiByKpi(mileStoneId: number, kpiIndex: number): Observable<void> {
    const url = `api/milestone/${mileStoneId}/kpi/${kpiIndex}`
    return this.httpClient.delete<void>(url);
  }
}
