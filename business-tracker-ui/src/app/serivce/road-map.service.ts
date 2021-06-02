import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RoadMapToDisplay} from '../models/road-map/road-map-to-display';
import {HttpOperation} from './http-operation';
import {RoadMapToAdd} from '../models/road-map/road-map-to-add';

@Injectable({
  providedIn: 'root'
})
export class RoadMapService extends HttpOperation<RoadMapToDisplay, RoadMapToAdd> {

  constructor(private http: HttpClient) {
    super('api/roadMaps', 'api/roadMaps', 'api/roadMaps', 'api/roadMaps', http);
  }
}
