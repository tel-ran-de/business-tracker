import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MemberToDisplay} from "../../../../../models/member/member-to-display";
import {Observable, Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {SprintService} from "../../../../../serivce/sprint.service";
import {DeliveryService} from "../../../../../serivce/delivery.service";
import {ResourceService} from "../../../../../serivce/resource.service";
import {MemberService} from "../../../../../serivce/member.service";
import {ResponsibleMembersService} from "../../../../../serivce/responsible-members.service";
import {SprintToDisplay} from "../../../../../models/sprint/sprint-to-display";
import {DeliveryToDisplay} from "../../../../../models/delivery/delivery-to-display";
import {ResourceToDisplay} from "../../../../../models/resource/resource-to-display";

@Component({
  selector: 'app-edit-sprint',
  templateUrl: './edit-sprint.component.html',
  styleUrls: ['./edit-sprint.component.css']
})
export class EditSprintComponent implements OnInit, OnDestroy {

  form: FormGroup;
  private taskId: string;
  private sprintId: string;
  subscriptions: Subscription[] = [];

  selectedMember: MemberToDisplay;
  sprint: SprintToDisplay;

  delivery: DeliveryToDisplay;
  deliveries: DeliveryToDisplay[] = [
    {name: 'Документ', sprintId: undefined, id: 1},
    {name: 'Презентация', sprintId: undefined, id: 2}
  ];
  members: Observable<MemberToDisplay[]>;
  private resources: ResourceToDisplay[] = [];
  private resourceIdsToRemove: number[] = [];
  @Output()
  private sprintFound: EventEmitter<boolean> = new EventEmitter<boolean>();
  private resourcesToAdd: ResourceToDisplay[] = [];

  constructor(private fb: FormBuilder,
              private route: ActivatedRoute,
              private sprintService: SprintService,
              private deliveryService: DeliveryService,
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

    const resourcesGetAllSub = this.resourceService.getAllByParams(this.sprintId, 'sprintId')
      .subscribe(value => {
        this.resources.push(...value);
        this.resourceService.reloadAddedResourceList$.next(this.resources);
      });

    const transferSubscribe = this.resourceService.addResourceTransfer$
      .subscribe(resourceToAdd => {
        resourceToAdd.sprintId = +this.sprintId;

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

    let sprintOk = false;
    let deliveryOk = false;
    let memberOk = false;
    let addResourceOk = false;
    let removeResourceOk = false;
    //Sprint
    this.sprint.name = this.form.controls.name.value;
    const addSprintSub = this.sprintService.updateById(+this.sprintId, this.sprint)
      .subscribe(() => {
        sprintOk = true;
        if (memberOk && deliveryOk && addResourceOk && removeResourceOk) {
          this.navigateToTaskPage();
        }
      }, error => console.error(error));
    //Delivery
    this.delivery.name = this.form.controls.delivery.value.name;
    const updateDeliverySub = this.deliveryService.updateById(this.delivery.id, this.delivery)
      .subscribe(() => {
        deliveryOk = true
        if (memberOk && sprintOk && addResourceOk && removeResourceOk) {
          this.navigateToTaskPage();
        }
      }, error => console.error(error));
    //Responsible member
    const updateBody: MemberToDisplay = this.form.controls.member.value;
    updateBody.id = this.selectedMember.id;
    updateBody.sprintId = this.selectedMember.sprintId;
    const updateResponseMemberSub = this.responsibleMembersService.updateById(this.selectedMember.id, updateBody)
      .subscribe(() => {
        memberOk = true;
        if (sprintOk && deliveryOk && addResourceOk && removeResourceOk) {
          this.navigateToTaskPage();
        }
      }, error => console.error(error));
    //resources add
    if (this.resourcesToAdd.length > 0) {
      this.resourcesToAdd.forEach((resourceToAdd, index) => {
        const addResSubsc = this.resourceService.add(resourceToAdd)
          .subscribe(() => {
              if (index === this.resourcesToAdd.length - 1 && sprintOk && deliveryOk && memberOk && removeResourceOk) {
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
            if (index === this.resourceIdsToRemove.length - 1 && sprintOk && deliveryOk && memberOk && addResourceOk) {
              removeResourceOk = true;
              this.navigateToTaskPage();
            }

          }, error => console.error(error));
        this.subscriptions.push(removeSubsc);

      });
    } else {
      removeResourceOk = true;
    }


    this.subscriptions.push(addSprintSub, updateResponseMemberSub, updateDeliverySub);
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

  compareDelivery(c1: DeliveryToDisplay, c2: DeliveryToDisplay): boolean {
    return c1 && c2 ? c1.name === c2.name : false;
  }

  private initForm() {
    this.form = this.fb.group({
      name: ['', Validators.required],
      member: ['', Validators.required],
      delivery: ['', Validators.required]
    });
  }

  private setFormValues() {

    this.sprintService.getById(this.sprintId)
      .subscribe(value => {
        this.sprintFound.emit(true);
        this.form.controls.name.patchValue(value.name);
        this.sprint = value;
      }, () => this.sprintFound.emit(false));

    const selectedMemberSubscr = this.responsibleMembersService.getAllByParams(this.sprintId, 'sprintId')
      .subscribe(value => {
        this.selectedMember = value[0];
        this.form.controls.member.patchValue(value[0]);
      });

    const selectedDeliverySubscribe = this.deliveryService.getAllByParams('^' + this.sprintId + '$', 'sprintId')
      .subscribe(value => {
        this.delivery = value[0];
        this.form.controls.delivery.patchValue(value[0]);
      });

    this.subscriptions.push(selectedMemberSubscr, selectedDeliverySubscribe)
  }
}
