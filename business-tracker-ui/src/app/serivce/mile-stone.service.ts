import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {MileStoneToDisplay} from '../models/mile-stone/mile-stone-to-display';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MileStoneService {

  options = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private httpClient: HttpClient) {
  }

  getAllMileStonesByRoadMapId(roadMapId: number): Observable<MileStoneToDisplay[]> {
    const url = `api/milestones/roadmap/${roadMapId}`
    return this.httpClient.get<MileStoneToDisplay[]>(url, this.options);
  }

  getMileStoneById(mileStoneId: number): Observable<MileStoneToDisplay> {
    const url = `api/milestones/${mileStoneId}`
    return this.httpClient.get<MileStoneToDisplay>(url, this.options);
  }
}
