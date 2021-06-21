import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {HttpOperation} from './http-operation';
import {TaskToDisplay} from '../models/task/task-to-display';
import {TaskToAdd} from '../models/task/task-to-add';
import {MemberToAdd} from "../models/member/member-to-add";
import {ResourceToAdd} from "../models/resource/resource-to-add";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TaskService extends HttpOperation<TaskToDisplay, TaskToAdd> {

  constructor(private http: HttpClient) {
    super('api/task', 'api/task', 'api/task', 'api/task', http);
  }

  addAll(arr: { task: TaskToDisplay, member: MemberToAdd, resources: ResourceToAdd[] }): Observable<any> {
    return this.http.post<TaskToDisplay>('api/task', arr, {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    });
  }
}
