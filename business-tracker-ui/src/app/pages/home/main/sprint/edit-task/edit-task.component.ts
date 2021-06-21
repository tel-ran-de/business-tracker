import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MemberToDisplay} from "../../../../../models/member/member-to-display";
import {Observable, Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {TaskService} from "../../../../../serivce/task.service";
import {ResourceService} from "../../../../../serivce/resource.service";
import {MemberService} from "../../../../../serivce/member.service";
import {ResponsibleMembersService} from "../../../../../serivce/responsible-members.service";
import {TaskToDisplay} from "../../../../../models/task/task-to-display";
import {ResourceToDisplay} from "../../../../../models/resource/resource-to-display";

@Component({
  selector: 'app-edit-task',
  templateUrl: './edit-task.component.html',
  styleUrls: ['./edit-task.component.css']
})
export class EditTaskComponent implements OnInit, OnDestroy {

  form: FormGroup;
  private taskId: string;
  private sprintId: string;
  subscriptions: Subscription[] = [];

  selectedMember: MemberToDisplay;
  task: TaskToDisplay;

  deliveries: string[] = ['Document', 'Presentation', "Nothing"];

  members: Observable<MemberToDisplay[]>;
  private resources: ResourceToDisplay[] = [];
  private resourceIdsToRemove: number[] = [];
  @Output()
  private sprintFound: EventEmitter<boolean> = new EventEmitter<boolean>();
  private resourcesToAdd: ResourceToDisplay[] = [];

  constructor(private fb: FormBuilder,
              private route: ActivatedRoute,
              private taskService: TaskService,
              private resourceService: ResourceService,
              public memberService: MemberService,
              private responsibleMembersService: ResponsibleMembersService,
              private router: Router) {
  }


  ngOnInit(): void {
    this.taskId = this.route.snapshot.paramMap.get('taskId');
    this.sprintId = this.route.snapshot.paramMap.get("sprintId");
    this.members = this.memberService.getAll();

    this.initForm();
    this.setFormValues();

    const resourcesGetAllSub = this.resourceService.getAllByParams(this.sprintId, 'taskId')
      .subscribe(value => {
        this.resources.push(...value);
        this.resourceService.reloadAddedResourceList$.next(this.resources);
      });

    const transferSubscribe = this.resourceService.addResourceTransfer$
      .subscribe(resourceToAdd => {
        resourceToAdd.sprintId = +this.taskId;

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
    let memberOk = false;
    let addResourceOk = false;
    let removeResourceOk = false;
    //Task
    this.task.name = this.form.controls.name.value;
    const updateTaskSub = this.taskService.updateById(this.task.id, this.task)
      .subscribe(() => {
        taskOk = true;
        if (memberOk && addResourceOk && removeResourceOk) {
          this.navigateToTaskPage();
        }
      }, error => console.error(error));

    //Responsible member
    if (this.form.controls.member.value) {
      const updateBodyMember: MemberToDisplay = this.form.controls.member.value;
      updateBodyMember.id = this.selectedMember.id;
      updateBodyMember.taskId = this.selectedMember.taskId;
      const updateResponseMemberSub = this.responsibleMembersService.updateById(this.selectedMember.id, updateBodyMember)
        .subscribe(() => {
          memberOk = true;
          if (taskOk && addResourceOk && removeResourceOk) {
            this.navigateToTaskPage();
          }
        }, error => console.error(error));
      this.subscriptions.push(updateResponseMemberSub);
    } else {
      memberOk = true;
    }

    //resources add
    if (this.resourcesToAdd.length > 0) {
      this.resourcesToAdd.forEach((resourceToAdd, index) => {
        const addResSubsc = this.resourceService.add(resourceToAdd)
          .subscribe(() => {
              if (index === this.resourcesToAdd.length - 1 && taskOk && memberOk && removeResourceOk) {
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
        const removeSubsc = this.resourceService.removeById(id)
          .subscribe(() => {
            if (index === this.resourceIdsToRemove.length - 1 && taskOk && memberOk && addResourceOk) {
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

  ngOnDestroy(): void {
    for (const subscription of this.subscriptions) {
      if (subscription) {
        subscription.unsubscribe();
      }
    }
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
      // name: ['', Validators.required],
      // member: ['', Validators.required],
      name: ['', Validators.required],
      member: [],
      delivery: []
    });
  }

  private setFormValues() {

    const getTaskByIdSubscr = this.taskService.getById(this.sprintId)
      .subscribe(value => {
        this.sprintFound.emit(true);
        this.form.controls.name.patchValue(value.name);
        this.form.controls.delivery.patchValue(value.delivery);
        this.task = value;
      }, () => this.sprintFound.emit(false));

    const selectedMemberSubscr = this.responsibleMembersService.getAllByParams(this.sprintId, 'taskId')
      .subscribe(value => {
        this.selectedMember = value[0];
        this.form.controls.member.patchValue(value[0]);
      });

    this.subscriptions.push(selectedMemberSubscr, getTaskByIdSubscr)
  }
}
