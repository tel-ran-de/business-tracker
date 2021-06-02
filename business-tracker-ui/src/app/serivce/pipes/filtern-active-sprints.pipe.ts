import {Pipe, PipeTransform} from '@angular/core';
import {SprintToDisplay} from '../../models/sprint/sprint-to-display';

@Pipe({
  name: 'filterActiveSprints'
})
export class FilterActiveSprintsPipe implements PipeTransform {

  transform(sprints: SprintToDisplay[]): SprintToDisplay[] {
    if (!sprints) {
      return [];
    }
    return sprints.filter(value => value.active);
  }
}
