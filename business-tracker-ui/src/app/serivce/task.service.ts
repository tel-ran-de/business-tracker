import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HttpOperation} from './http-operation';
import {TaskToDisplay} from '../models/task/task-to-display';
import {TaskToAdd} from '../models/task/task-to-add';

@Injectable({
  providedIn: 'root'
})
export class TaskService extends HttpOperation<TaskToDisplay, TaskToAdd> {

  constructor(private http: HttpClient) {
    super('api/tasks', 'api/tasks', 'api/tasks', 'api/tasks', http);
  }
}
