import {Component} from '@angular/core';
import {ResourceService} from '../../../../../serivce/resource.service';
import {ResourceToDisplay} from '../../../../../models/resource/resource-to-display';

@Component({
  selector: 'app-resource-list',
  templateUrl: './resource-list.component.html',
  styleUrls: ['./resource-list.component.css']
})
export class ResourceListComponent {
  openForm = false;

  constructor(public resourceService: ResourceService) {
  }

  onClickRemoveRes(res: ResourceToDisplay): void {
    this.resourceService.reloadResourceListRemoved$.next(res);
  }
}
