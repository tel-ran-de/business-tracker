import {Member} from './member';
import {Task} from './task';

export class RoadMap {
  id: number;

  name: string;

  members: Member[];
  tasks: Task[];
}
