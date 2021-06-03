import {Injectable} from '@angular/core';
import {HttpOperation} from './http-operation';
import {HttpClient} from '@angular/common/http';
import {ResourceToDisplay} from '../models/resource/resource-to-display';
import {ResourceToAdd} from '../models/resource/resource-to-add';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ResourceService extends HttpOperation<ResourceToDisplay, ResourceToAdd> {

  addResourceTransfer$: Subject<ResourceToAdd> = new Subject<ResourceToAdd>();

  reloadAddedResourceList$: Subject<ResourceToAdd[]> = new Subject<ResourceToAdd[]>();
  reloadResourceListRemoved$: Subject<ResourceToDisplay> = new Subject<ResourceToDisplay>();

  constructor(private http: HttpClient) {
    super('api/resource', 'api/resource', 'api/resource', 'api/resource', http);
  }

  transferAddedResource(addedResource: ResourceToAdd): void {
    this.addResourceTransfer$.next(addedResource);
  }
}
