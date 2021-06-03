import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {TaskToDisplay} from '../../../../../models/task/task-to-display';
import {TaskService} from '../../../../../serivce/task.service';
import {Subscription} from 'rxjs';
import {DeliveryService} from '../../../../../serivce/delivery.service';
import {ResourceService} from '../../../../../serivce/resource.service';
import {ResponsibleMembersService} from '../../../../../serivce/responsible-members.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DeleteConfirmationModalComponent} from '../../delete-confirmation-modal/delete-confirmation-modal.component';

@Component({
  selector: 'app-task-element',
  templateUrl: './task-element.component.html',
  styleUrls: ['./task-element.component.css']
})
export class TaskElementComponent implements OnInit, OnDestroy {

  @Input()
  mileStoneId: number;

  tasks: TaskToDisplay[];
  subscriptions: Subscription[] = [];

  constructor(private taskService: TaskService,
              private deliveryService: DeliveryService,
              private resourceService: ResourceService,
              private responsibleMembersService: ResponsibleMembersService,
              private modal: NgbModal) {
  }

  ngOnInit(): void {
    const mileStoneId = String(this.mileStoneId);

    const getAllSprintsByIdSubscription = this.taskService.getAllByParams(mileStoneId, 'mileStoneId')
      .subscribe(value => this.tasks = value, error => console.log(error));
    this.subscriptions.push(getAllSprintsByIdSubscription);
  }

  onClickRemoveSprint(sprint: TaskToDisplay): void {

    const removeConfirmModal = this.modal.open(DeleteConfirmationModalComponent);
    removeConfirmModal.result.then(value => {
        if (value === 'ok') {
          const removeSprintByIdSub = this.taskService.removeById(sprint.id)
            .subscribe(() => {

              const getAllResourceSub = this.resourceService.getAll()
                .subscribe(resources => {
                  this.resourceService.reloadAddedResourceList$.next(resources);

                  const index = this.tasks.indexOf(sprint);
                  this.tasks.splice(index, 1);
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
