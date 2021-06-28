import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {TaskToDisplay} from '../models/task/task-to-display';
import {TaskToAdd} from '../models/task/task-to-add';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  options = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  getAllTasksByMileStoneId(mileStoneId: number): Observable<TaskToDisplay[]> {
    const url = `api/tasks/milestone/${mileStoneId}`
    return this.http.get<TaskToDisplay[]>(url, this.options);
  }

  getAllTasksByProjectAndActive(projectId: number) {
    const url = `api/tasks/project/${projectId}/active`
    return this.http.get<TaskToDisplay[]>(url, this.options);
  }

  addTask(arr: TaskToAdd): Observable<void> {
    return this.http.post<void>('api/tasks', arr, this.options);
  }

  updateTask(task: TaskToDisplay): Observable<void> {
    const url = `api/tasks`
    return this.http.put<void>(url, task, this.options);
  }

  removeTaskById(taskId: number): Observable<void> {
    const url = `api/tasks/${taskId}`
    return this.http.delete<void>(url, this.options);
  }

  getTaskById(taskId: string): Observable<TaskToDisplay> {
    const url = `api/tasks/${taskId}`
    return this.http.get<TaskToDisplay>(url, this.options);
  }
}
