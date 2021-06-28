import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MemberToDisplay} from "../../../../../models/member/member-to-display";
import {Observable, Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {TaskService} from "../../../../../serivce/task.service";
import {ResourceService} from "../../../../../serivce/resource.service";
import {MemberService} from "../../../../../serivce/member.service";
import {TaskToDisplay} from "../../../../../models/task/task-to-display";
import {ResourceToDisplay} from "../../../../../models/resource/resource-to-display";

@Component({
  selector: 'app-edit-task',
  templateUrl: './edit-task.component.html',
  styleUrls: ['./edit-task.component.css']
})
export class EditTaskComponent implements OnInit, OnDestroy {

  form: FormGroup;
  private mileStoneId: string;
  private taskId: string;
  private subscriptions: Subscription[] = [];

  selectedMember: MemberToDisplay;
  task: TaskToDisplay;

  deliveries: string[] = ['Document', 'Presentation', "Other", "Nothing"];

  members: Observable<MemberToDisplay[]>;
  private resources: ResourceToDisplay[] = [];
  private resourceIdsToRemove: number[] = [];
  @Output()
  private taskIsFound: EventEmitter<boolean> = new EventEmitter<boolean>();
  private resourcesToAdd: ResourceToDisplay[] = [];

  constructor(private fb: FormBuilder,
              private route: ActivatedRoute,
              private taskService: TaskService,
              private resourceService: ResourceService,
              public memberService: MemberService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.mileStoneId = this.route.snapshot.paramMap.get('mileStoneId');
    this.taskId = this.route.snapshot.paramMap.get("taskId");
    const projectId = +this.route.snapshot.paramMap.get("projectId");
    this.members = this.memberService.getAllMembersByProjectId(projectId);

    this.initForm();
    this.setFormValues();

    const resourcesGetAllSub = this.resourceService.getAllResourcesByTaskId(+this.taskId)
      .subscribe(value => {
        this.resources.push(...value);
        this.resourceService.reloadAddedResourceList$.next(this.resources);
      });

    const transferSubscribe = this.resourceService.addResourceTransfer$
      .subscribe(resourceToAdd => {
        resourceToAdd.taskId = +this.taskId;

        this.resources.push(resourceToAdd);
        this.resourcesToAdd.push(resourceToAdd);
      });

    const resRemovedSub = this.resourceService.reloadResourceListRemoved$
      .subscribe(resourceToRemove => {
        this.resourceIdsToRemove.push(resourceToRemove.id);

        const removedResIndex = this.resources.indexOf(resourceToRemove);
        this.resources.splice(removedResIndex, 1);
      });

    this.subscriptions.push(transferSubscribe, resRemovedSub, resourcesGetAllSub);
  }

  onSubmit(): void {

    let taskOk = false;
    let addResourceOk = false;
    let removeResourceOk = false;
    //Task
    this.task.name = this.form.controls.name.value;
    this.task.delivery = this.form.controls.delivery.value;
    this.task.memberId = this.form.controls.member.value.id;
    const updateTaskSub = this.taskService.updateTask(this.task)
      .subscribe(() => {
        taskOk = true;
        if (addResourceOk && removeResourceOk) {
          this.navigateToTaskPage();
        }
      }, error => console.error(error));

    //resources add
    if (this.resourcesToAdd.length > 0) {
      this.resourcesToAdd.forEach((resourceToAdd, index) => {
        const addResSubsc = this.resourceService.addResource(resourceToAdd)
          .subscribe(() => {
              if (index === this.resourcesToAdd.length - 1 && taskOk && removeResourceOk) {
                addResourceOk = true;
                this.navigateToTaskPage();
              }
            },
            error => console.error(error)
          );
        this.subscriptions.push(addResSubsc);
      })
    } else {
      addResourceOk = true;
    }
    //resource remove
    if (this.resourceIdsToRemove.length > 0) {
      this.resourceIdsToRemove.forEach((id, index) => {
        const removeSubsc = this.resourceService.removeResource(id)
          .subscribe(() => {
            if (index === this.resourceIdsToRemove.length - 1 && taskOk && addResourceOk) {
              removeResourceOk = true;
              this.navigateToTaskPage();
            }

          }, error => console.error(error));
        this.subscriptions.push(removeSubsc);

      });
    } else {
      removeResourceOk = true;
    }

    this.subscriptions.push(updateTaskSub);
  }

  navigateToTaskPage(): void {
    this.router.navigate(['../../../..'], {relativeTo: this.route});
  }

  compareMembers(c1: MemberToDisplay, c2: MemberToDisplay): boolean {
    return c1 && c2 ?
      c1.name === c2.name && c1.lastName === c2.lastName && c1.img === c2.img && c1.position === c2.position
      : c1 === c2;
  }

  compareDelivery(c1: string, c2: string): boolean {
    return c1 && c2 ? c1 === c2 : false;
  }

  private initForm() {
    this.form = this.fb.group({
      name: ['', Validators.required],
      member: [],
      delivery: []
    });
  }

  private setFormValues() {

    const getTaskByIdSubscr = this.taskService.getTaskById(this.taskId)
      .subscribe(value => {
          this.taskIsFound.emit(true);
          this.form.controls.name.patchValue(value.name);
          this.form.controls.delivery.patchValue(value.delivery);
          this.task = value;

          const selectedMemberSubscr = this.memberService.getMemberById(this.task.memberId)
            .subscribe(value => {
              this.selectedMember = value;
              this.form.controls.member.patchValue(value);
            });
          this.subscriptions.push(selectedMemberSubscr)

        },
        () => this.taskIsFound.emit(false));

    this.subscriptions.push(getTaskByIdSubscr)
  }

  ngOnDestroy(): void {
    for (const subscription of this.subscriptions) {
      if (subscription) {
        subscription.unsubscribe();
      }
    }
  }

  getDate(): string {
    return new Date().toISOString().split("T")[0];
  }
}
