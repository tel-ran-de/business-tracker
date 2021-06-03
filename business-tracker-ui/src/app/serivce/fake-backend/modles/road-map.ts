import {Member} from './member';
import {MileStone} from './mileStone';

export class RoadMap {
  id: number;
  name: string;

  members: Member[];
  mileStones: MileStone[];
}
