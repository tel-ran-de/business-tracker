import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ResourceToDisplay} from '../models/resource/resource-to-display';
import {ResourceToAdd} from '../models/resource/resource-to-add';
import {Observable, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ResourceService {

  options = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  addResourceTransfer$: Subject<ResourceToAdd> = new Subject<ResourceToAdd>();

  reloadAddedResourceList$: Subject<ResourceToAdd[]> = new Subject<ResourceToAdd[]>();
  reloadResourceListRemoved$: Subject<ResourceToDisplay> = new Subject<ResourceToDisplay>();

  constructor(private httpClient: HttpClient) {
  }

  transferAddedResource(addedResource: ResourceToAdd): void {
    this.addResourceTransfer$.next(addedResource);
  }

  getAllResourcesByRoadMap(roadMapId: number): Observable<ResourceToDisplay[]> {
    const url = `api/resources/roadmap/${roadMapId}`
    return this.httpClient.get<ResourceToDisplay[]>(url, this.options);
  }

  getAllResourcesByTaskId(taskId: number): Observable<ResourceToDisplay[]> {
    const url = `api/resources/task/${taskId}`
    return this.httpClient.get<ResourceToDisplay[]>(url, this.options);
  }

  addResource(resourceToAdd: ResourceToDisplay): Observable<void> {
    const url = `api/resources`
    return this.httpClient.post<void>(url, resourceToAdd, this.options);
  }

  removeResource(id: number): Observable<void> {
    const url = `api/resources/${id}`
    return this.httpClient.delete<void>(url, this.options);
  }
}
