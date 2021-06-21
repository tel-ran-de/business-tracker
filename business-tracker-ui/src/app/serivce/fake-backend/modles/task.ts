import {Member} from './member';
import {Resource} from './resource';

export class Task {
  id: number;
  mileStoneId: number;

  name: string;
  active: boolean;
  finished: boolean;
  delivery: string;

  responsibleMembers: Member[];
  resources: Resource[];
}
