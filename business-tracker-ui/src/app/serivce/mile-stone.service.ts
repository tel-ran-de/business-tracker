import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HttpOperation} from './http-operation';
import {MileStoneToDisplay} from '../models/mile-stone/mile-stone-to-display';
import {MileStoneToAdd} from '../models/mile-stone/mile-stone-to-add';

@Injectable({
  providedIn: 'root'
})
export class MileStoneService extends HttpOperation<MileStoneToDisplay, MileStoneToAdd> {

  constructor(private http: HttpClient) {
    super('api/milestone', 'api/milestone', 'api/milestone', 'api/milestone', http);
  }

}
