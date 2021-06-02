import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {HttpOperation} from './http-operation';
import {SprintToDisplay} from '../models/sprint/sprint-to-display';
import {SprintToAdd} from '../models/sprint/sprint-to-add';
import {MemberToAdd} from '../models/member/member-to-add';
import {DeliveryToAdd} from '../models/delivery/delivery-to-add';
import {ResourceToAdd} from '../models/resource/resource-to-add';

@Injectable({
  providedIn: 'root'
})
export class SprintService extends HttpOperation<SprintToDisplay, SprintToAdd> {


  constructor(private http: HttpClient) {
    super('api/sprints', 'api/sprints', 'api/sprints', 'api/sprints', http);
  }

  addAll(arr: { sprint: SprintToAdd, member: MemberToAdd, delivery: DeliveryToAdd, resources: ResourceToAdd[] }) {
    return this.http.post<SprintToDisplay>('api/sprints', arr, {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    });
  }
}
