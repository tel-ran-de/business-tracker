import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {TaskToDisplay} from '../../../../../models/task/task-to-display';
import {TaskService} from '../../../../../serivce/task.service';
import {Subscription} from 'rxjs';
import {ResourceService} from '../../../../../serivce/resource.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DeleteConfirmationModalComponent} from '../../delete-confirmation-modal/delete-confirmation-modal.component';
import {ActivatedRoute} from "@angular/router";

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
              private resourceService: ResourceService,
              private modal: NgbModal,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const getAllTasksByMileStoneIdSubscription = this.taskService.getAllTasksByMileStoneId(this.mileStoneId)
      .subscribe(value => {
          this.tasks = value;
          this.tasks.sort((a, b) => a.id - b.id);
        },
        error => console.log(error));

    this.subscriptions.push(getAllTasksByMileStoneIdSubscription);
  }

  onClickRemoveTask(task: TaskToDisplay): void {

    const removeConfirmModal = this.modal.open(DeleteConfirmationModalComponent);
    removeConfirmModal.result.then(value => {
        if (value === 'ok') {
          const removeTaskByIdSub = this.taskService.removeTaskById(task.id)
            .subscribe(() => {

              const roadMapId = +this.route.snapshot.paramMap.get("roadMapId");
              const getAllResourceSub = this.resourceService.getAllResourcesByRoadMap(roadMapId)
                .subscribe(resources => {
                  this.resourceService.reloadAddedResourceList$.next(resources);

                  const index = this.tasks.indexOf(task);
                  this.tasks.splice(index, 1);
                });
              this.subscriptions.push(getAllResourceSub);

            }, error => console.log(error));

          this.subscriptions.push(removeTaskByIdSub);
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
