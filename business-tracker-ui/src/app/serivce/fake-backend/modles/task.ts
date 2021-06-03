import {Member} from './member';
import {Resource} from './resource';
import {Delivery} from './delivery';

export class Task {
  id: number;
  mileStoneId: number;

  name: string;
  active: boolean;
  finished: boolean;

  responsibleMembers: Member[];
  resources: Resource[];
  delivers: Delivery[];

}
