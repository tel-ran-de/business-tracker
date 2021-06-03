import {Injectable} from '@angular/core';
import {HttpOperation} from './http-operation';
import {DeliveryToAdd} from '../models/delivery/delivery-to-add';
import {HttpClient} from '@angular/common/http';
import {DeliveryToDisplay} from '../models/delivery/delivery-to-display';

@Injectable({
  providedIn: 'root'
})
export class DeliveryService extends HttpOperation<DeliveryToDisplay, DeliveryToAdd> {

  constructor(private http: HttpClient) {
    super('api/delivery', 'api/delivery', 'api/delivery', 'api/delivery', http);
  }
}
