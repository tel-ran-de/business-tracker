import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {RoadMapToDisplay} from '../models/road-map/road-map-to-display';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RoadMapService {

  options = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private httpClient: HttpClient) {
  }

  getAllRoadMapsByProjectId(projectId: number): Observable<RoadMapToDisplay[]> {
    const url = `api/roadmaps/project/${projectId}`
    return this.httpClient.get<RoadMapToDisplay[]>(url, this.options);
  }
}
