import { Component, OnInit } from '@angular/core';
import {BookApi} from "../service/api/BookApi";
import {Book} from "../service/model/book";

@Component({
  selector: 'bs-book-list',
  templateUrl: './book-list.component.html',
  styles: []
})
export class BookListComponent implements OnInit {

  private nbBooks: number;
  private books: Book[];
  constructor(private bookService: BookApi) { }

  ngOnInit() {
    this.bookService.countBooks().subscribe(nbBooks => this.nbBooks = nbBooks);
    this.bookService.getBooks().subscribe(books => this.books = books);
  }
}