/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

export interface MessageResponse {
  content?: string;
  createdAt?: string;
  id?: number;
  media?: Array<string>;
  receiverId?: string;
  senderId?: string;
  state?: 'SENT' | 'SEEN' | 'DELIVERED' | 'READ';
  type?: 'TEXT' | 'IMAGE' | 'VIDEO' | 'AUDIO' | 'DOCUMENT';
}
