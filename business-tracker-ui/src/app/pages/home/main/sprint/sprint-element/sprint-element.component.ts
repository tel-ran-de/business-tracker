import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {SprintToDisplay} from '../../../../../models/sprint/sprint-to-display';
import {SprintService} from '../../../../../serivce/sprint.service';
import {Subscription} from 'rxjs';
import {DeliveryService} from '../../../../../serivce/delivery.service';
import {ResourceService} from '../../../../../serivce/resource.service';
import {ResponsibleMembersService} from '../../../../../serivce/responsible-members.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DeleteConfirmationModalComponent} from '../../delete-confirmation-modal/delete-confirmation-modal.component';

@Component({
  selector: 'app-sprint-elements',
  templateUrl: './sprint-element.component.html',
  styleUrls: ['./sprint-element.component.css']
})
export class SprintElementComponent implements OnInit, OnDestroy {

  @Input()
  taskId: number;

  sprints: SprintToDisplay[];
  subscriptions: Subscription[] = [];

  constructor(private sprintService: SprintService,
              private deliveryService: DeliveryService,
              private resourceService: ResourceService,
              private responsibleMembersService: ResponsibleMembersService,
              private modal: NgbModal) {
  }

  ngOnInit(): void {
    const taskId = String(this.taskId);

    const getAllSprintsByIdSubscription = this.sprintService.getAllByParams(taskId, 'taskId')
      .subscribe(value => this.sprints = value);
    this.subscriptions.push(getAllSprintsByIdSubscription);
  }

  onClickRemoveSprint(sprint: SprintToDisplay): void {

    const removeConfirmModal = this.modal.open(DeleteConfirmationModalComponent);
    removeConfirmModal.result.then(value => {
        if (value === 'ok') {
          const removeSprintByIdSub = this.sprintService.removeById(sprint.id)
            .subscribe(() => {

              const getAllResourceSub = this.resourceService.getAll()
                .subscribe(resources => {
                  this.resourceService.reloadAddedResourceList$.next(resources);

                  const index = this.sprints.indexOf(sprint);
                  this.sprints.splice(index, 1);
                });
              this.subscriptions.push(getAllResourceSub);

            }, error => console.log(error));

          this.subscriptions.push(removeSprintByIdSub);

        }
      }
    );
  }

  ngOnDestroy(): void {
    for (const subscription of this.subscriptions) {
      if (subscription) {
        subscription.unsubscribe();
      }
    }
  }
}
