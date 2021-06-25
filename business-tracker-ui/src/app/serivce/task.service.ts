import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {HttpOperation} from './http-operation';
import {TaskToDisplay} from '../models/task/task-to-display';
import {TaskToAdd} from '../models/task/task-to-add';
import {ResourceToAdd} from "../models/resource/resource-to-add";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TaskService extends HttpOperation<TaskToDisplay, TaskToAdd> {

  constructor(private http: HttpClient) {
    super('api/task', 'api/task', 'api/task', 'api/task', http);
  }

  getAllTasksByMileStoneId(mileStoneId: number): Observable<TaskToDisplay[]> {
    const url = `api/tasks/milestone/${mileStoneId}`
    return this.http.get<TaskToDisplay[]>(url, {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    });
  }

  getAllTasksByActive(isActive: boolean) {
    return this.http.get<TaskToDisplay[]>('api/tasks/by', {
      params: new HttpParams().set("active", String(isActive)),
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    });
  }

  addTask(arr: { task: TaskToDisplay, resources: ResourceToAdd[] }): Observable<void> {
    return this.http.post<void>('api/tasks', arr, {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    });
  }
}
