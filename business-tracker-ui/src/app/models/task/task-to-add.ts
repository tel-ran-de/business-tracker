import {ResourceToAdd} from "../resource/resource-to-add";

export class TaskToAdd {
  id: number;
  mileStoneId: number;
  memberId: number;
  delivery: string;

  name: string;
  active: boolean;
  finished: boolean;
  resources: ResourceToAdd[];
}
